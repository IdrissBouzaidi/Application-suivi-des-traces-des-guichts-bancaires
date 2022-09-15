import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { type } from 'jquery';
import { AgenceService } from 'src/app/services/agence.service';
import { GabService } from 'src/app/services/gab.service';
import { RegionService } from 'src/app/services/region.service';
import { TraceService } from 'src/app/services/trace.service';
import { UtilisateurService } from 'src/app/services/utilisateur.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-objetsorigine',
  templateUrl: './objetsorigine.component.html',
  styleUrls: ['./objetsorigine.component.css']
})
export class ObjetsorigineComponent implements OnInit {

  attributs = new Array();
  typeElements: any;
  _etatTransition: number = 0;
  reference: any;//La clé étrangère (dans la majorité des cas)
  elementsOrigine =  new Array();//Tous les éléments (<= 100) qu'on a récupérer du serveur.
  elements =  new Array();//Les elements correspondants à notre recherche.
  nombreLignes: number = 10;//Le nombre de lignes par page
  nombreLignesTotal: number;//Le nombre de lignes par page
  nombrePages: number;
  pageActuelle: number = 1;
  pageDestination: number = 1;
  ensembleDestination: number = 1;
  pages = new Array();
  rechercheActuel: any = "";
  ligneDebut: number = 0;//Par exemple s'il vaut 100, on va récupérer l'intervalle [101, 200] ...
  ligneDebutChargementBD: number = 0;//Par ex si ligneDebut = 210, cet attribut va avoir 200
  chargementData: boolean = false;//On lui donne 'true' quand on clique sur l'un des carreaux et que les données doivent se charger du serveur.
  modeFormulaire: boolean = false;
  referencesAvant = new Array();
  referencesApres = new Array();
  elementErreur = '<span id="messageErreur" style="font-size: 12px;text-align: right;font-weight: 600;color: #e74c3c;display: block;width: calc(100% - 10px);">';
  chemin: String;
  width: string;
  modificationAuorisee = false;
  etatModification = false;
  elementActuelAModifier: any;
  referenceSelected = new Array();
  profilActuel = sessionStorage.getItem('profil');
  nomActuel = sessionStorage.getItem('nom');
  prenomActuel = sessionStorage.getItem('prenom');
  affichagePage = false;
  elementDeTri = "";
  ascendre = true;

  constructor(protected _regionService: RegionService,
    protected _agenceService: AgenceService,
    protected _gabService: GabService,
    protected _traceService: TraceService,
    protected _utilisateurService: UtilisateurService,
    protected _activatedRoute: ActivatedRoute,
    protected _router: Router) { }

  ngOnInit(): void {
    this.reference = this._activatedRoute.snapshot.paramMap.get('id');
    var typeElements = this.getTypeElements();
    if(this.profilActuel == '3' && typeElements != 'traces'){
      this._router.navigateByUrl("/traces");
    }
    else if(this.profilActuel == '2' && typeElements != 'GABs' && typeElements != 'traces'){
      this._router.navigateByUrl("/traces");
    }
    else if(this.profilActuel == '2' && typeElements == 'GABs'){
      this.affichagePage = true;
      this.ajouter_modifier_Element(1);
    }
    else if(this.profilActuel == '1' && typeElements == ""){
      this._router.navigateByUrl("/regions");
    }
    else{
      this.affichagePage = true;
      this.attributs = new Array();
      if(!this.reference){
        this.reference = "";
      }
      if(typeElements == "regions"){
        this.width = "8px";
        this.typeElements = ["regions", "L'ajout d'une region", "La modification d'une région"];
        this.attributs.push(["Code région", "codReg", 0, ""], ["Libellé région", "libReg", 0, ""]);
        var elementTri = this.elementDeTri;
        if(this.ascendre == false){
          elementTri = "DESC" + elementTri;
        }
        this._regionService.getRegions(this.ligneDebutChargementBD, this.rechercheActuel, elementTri).subscribe(
          data => {
            this.elements = data.regions;
            this.init(data);
          }
        )
      }
      else if(typeElements == "agences"){
        this.width = "10px";
        this.typeElements = ["agences", "L'ajout d'une agence", "La modification d'une agence"];
        this.attributs.push(["Code burpo", "codeBurpo", 0, ""], ["Libellé burpo", "libelleBurpo", 0, ""], ["Libellé région", "libReg", 1, ""]);
        var elementTri = this.elementDeTri;
        if(this.ascendre == false){
          elementTri = "DESC" + elementTri;
        }
        this._agenceService.getAgences(this.reference, this.ligneDebutChargementBD, this.rechercheActuel, elementTri).subscribe(
          data => {
            this.elements = data.agences;
            this.init(data);
          }
        );
      }
      else if(typeElements == "GABs"){
        this.width = "48px";
        this.typeElements = ["GABs", "L'ajout d'un GAB", "La modification d'un GAB"];
        this.attributs.push(["Code GAB", "codeGAB", 0, ""], ["Libellé GAB", "libGAB", 0, ""], ["Url GAB", "urlGAB", 0, ""], ["Url GAB archive", "urlGABArchive", 0, ""], ["Date de la dernière remonée", "dateDernierRemontee", 2, ""], ["Libellé burpo", "libelleBurpo", 1, ""]);
        var elementTri = this.elementDeTri;
        if(this.ascendre == false){
          elementTri = "DESC" + elementTri;
        }
        this._gabService.getGABs(this.reference, this.ligneDebutChargementBD, this.rechercheActuel, elementTri).subscribe(
          data => {
            this.elements = data.gabs;
            this.init(data);
          }
        )
      }
      else if(typeElements == "traces"){
        this.width = "35px";
        this.typeElements = ["traces", "L'ajout d'une trace", "La modification d'une trace"];
        this.attributs.push(["ID", "idTrace", 2, ""], ["Nom du fichier", "nomFichier", 2, ""], ["Date de trace", "dateTrx", 2, ""], ["Date du remontés", "dateRemontee", 2, ""], ["Libellé GAB", "libGAB", 2, ""]);
        var elementTri = this.elementDeTri;
        if(this.ascendre == false){
          elementTri = "DESC" + elementTri;
        }
        this._traceService.getTraces(this.reference, this.ligneDebutChargementBD, this.rechercheActuel, elementTri).subscribe(
          data => {
            this.elements = data.traces;
            this.init(data);
          }
        )
      }
      else if(typeElements == "profils"){
        this.width = "47px";
        this.typeElements = ["profils", "L'ajout d'un utilisateur", "La modification d'un profil"];
        this.attributs.push(["Code", "codeU", 2, ""], ["nom", "nom", 0, ""], ["prenom", "prenom", 0, ""], ["email", "email", 0, ""], ["Mot de passe", "motDePasse", 0, ""], ["Profil", "profil", 1, ""]);
        var elementTri = this.elementDeTri;
        if(this.ascendre == false){
          elementTri = "DESC" + elementTri;
        }
        this._utilisateurService.getProfils(this.reference, this.ligneDebutChargementBD, this.rechercheActuel, elementTri).subscribe(
          data => {
            this.elements = data.utilisateurs;
            this.init(data);
          }
        )
      }
    }
  }
  init(data: any): void{
    this.nombreLignesTotal = data.nombreLignes;
    this.elementsOrigine = this.elements;
    this.nombrePages = Math.trunc(this.nombreLignesTotal/this.nombreLignes);
    if(this.chargementData == false){
      this._etatTransition = this.etatTransition(this.pageActuelle, this.pageDestination);
    }
    this.chargementData = false;
    this.couper(this.nombreLignes);
  }

  getTypeElements():any {
    var typeElements;
    var fin = 0;
    for(let i = 1; i<this._router.url.length; i++){
      if(this._router.url[i] == '/'){
        typeElements = this._router.url.substring(1, i);
        fin = i;
      }
    }
    if(fin  == 0){
      typeElements = this._router.url.substring(1);
    }
    return typeElements;
  }
  afficher_n_ligne(event: any): void{
    this.chargerData(1, this.rechercheActuel);
    const newVal = event.target.value;
    this.nombreLignes = parseInt(newVal.toString(), 10);
    this.pageActuelle = 1;
    this.couper(this.nombreLignes);
    $('.current').attr("class", "paginate_button");
    $('#p1').attr("class", "paginate_button current");
    this._etatTransition = this.etatTransition(1, 1);
  }
  couper(nombreLignes: number): void{
    var ligneDebut = this.ligneDebut%100;
    var ligneFin = (this.ligneDebut + nombreLignes)%100
    this.elements = this.elementsOrigine.slice(ligneDebut, ligneFin != 0? ligneFin: 100);
    this.pagesActuelles();
  }

  accederAuPageViaEvent(event: any): void{
    var destination = event.target.id;
    this.pageDestination = parseInt(destination.slice(1, destination.length), 10);

    this._etatTransition = this.etatTransition(this.pageActuelle, this.pageDestination);
    this.accederAuPage(this.pageDestination);
  }

  etatTransition(pageActuelle: any, pageDestination: any): any{
    if(this.nombrePages < 6){
      return 12;
    }
    else if(pageActuelle == 1 && pageDestination == 1){//Quand on a des ... et on veut colorer le 1er carreau
      return 13;
    }
    else if(this.nombrePages == 8){
      if(pageActuelle < 5 && pageDestination < 5){
        return 7;
      }
      else if(pageActuelle > 5 && pageDestination >= 5){
        return 9;
      }
      else if(pageActuelle <5 && pageDestination >= 5){
        if(pageDestination == 5){
          return 2;
        }
        else{
          return 11;
        }
      }
      else if(pageActuelle >= 5 && pageDestination < 5){
        if(pageDestination == 4){
          return 5;
        }
        else{
          return 10;
        }
      }
    }
    else if(pageActuelle < 5){//de (1) à
      if(pageDestination < 5){
        return 7;
      }
      else if(this.nombrePages - pageDestination > 3){
        return 1;
      }
      else{
        return 3;
      }
    }
    else if(this.nombrePages - pageActuelle > 3){//de (2) à
      if(pageDestination == 4){
        return 5;
      }
      else if(pageDestination == 1){
        return 10;
      }
      else if(this.nombrePages - pageDestination > 3){
        return 8;
      }
      else if(pageDestination == this.nombrePages){
        return 11;
      }
      else{
        return 2;
      }
    }
    else{//de (3) à
      if(pageDestination < 5){
        return 6;
      }
      else if(this.nombrePages - pageDestination > 3){
        return 4;
      }
      else{
        return 9;
      }
    }
  }
  accederAuPage(pageDestination: any): void{
    
    this.chargerData(pageDestination, this.rechercheActuel);
    this.pageActuelle = pageDestination;
    this.pagesActuelles();
    this.colorerCarreauPage(pageDestination);
  }

  colorerCarreauPage(pageDestination: any): void{
    if(![8, 13].includes(this._etatTransition)){
      $('.current').attr("class", "paginate_button");
    }
    if([7, 8, 9, 12, 13].includes(this._etatTransition) && this.pages[1].length == 0){
      $('.current').attr("class", "paginate_button");
      $("#p" + pageDestination).attr("class", "paginate_button current");
      $("#m" + pageDestination).attr("class", "paginate_button current");
      $("#d" + pageDestination).attr("class", "paginate_button current");
    }
  }

  chargerData(pageDestination: any, recherche: any): void{
    var donnees1 = Math.trunc((pageDestination-1)*this.nombreLignes/100);
    var donnees2 = Math.trunc((this.pageActuelle-1)*this.nombreLignes/100);
    if(donnees1 == donnees2){
      var debut = (pageDestination-1)*this.nombreLignes%100;
      var destination = pageDestination*this.nombreLignes%100;
      this.elements = this.elementsOrigine.slice(debut, destination!= 0? destination: 100);
    }
    else{
      this.ligneDebut = Math.trunc((pageDestination-1)*this.nombreLignes);
      this.ligneDebutChargementBD = Math.trunc((pageDestination-1)*this.nombreLignes/100) * 100;
      this.chargementData = true;
      this.ngOnInit();
    }
  }
  
  pagesActuelles(): void{
    this.pages = new Array();
    this.nombrePages = Math.trunc(this.nombreLignesTotal/this.nombreLignes);
    if(this.nombreLignesTotal % this.nombreLignes != 0){
      this.nombrePages += 1;
    }
    var pages1 = new Array();
    var pages2 = new Array();
    var pages3 = new Array();
    if(this.nombrePages > 6){
      if(this.pageActuelle < 5){
        $('#p4').show();
        $('#p5').show();
        for(let i = 2; i<4; i++){
          pages1.push(i);
        }
      }
      else if(this.nombrePages - this.pageActuelle >= 4){
        $('#p4').hide();
        $('#p5').hide();
        pages2.push(this.pageActuelle - 1);
        pages2.push(this.pageActuelle);
        pages2.push(this.pageActuelle + 1);
      }
      else{
        $('#p4').hide();
        $('#p5').hide();
        for(let i = this.nombrePages - 2; i<this.nombrePages; i++){
          pages3.push(i);
        }
      }
    }
    else{
      for(let i = 2; i<=this.nombrePages; i++){
        pages1.push(i);
      }
    }
    this.pages.push(pages1);
    this.pages.push(pages2);
    this.pages.push(pages3);
    this.traiterPrecedantNext();
  }


  traiterPrecedantNext(): void{
    if(this.pageActuelle == 1){
      $("#_previous").attr("class", "paginate_button previous disabled");
    }
    else{
      $("#_previous").attr("class", "paginate_button previous");
    }
    if(this.pageActuelle ==this.nombrePages){
      $("#_next").attr("class", "paginate_button previous disabled");
    }
    else{
      $("#_next").attr("class", "paginate_button previous");
    }
  }

  suivant_arriere(estSuivant: boolean): void{
    var pageDestination = this.pageActuelle;
    if(estSuivant == true){
      pageDestination += 1;
    }
    else{
      pageDestination -= 1;
    }
    if(pageDestination >= 1 && pageDestination <= this.nombrePages){
      this._etatTransition = this.etatTransition(this.pageActuelle, pageDestination);
      this.accederAuPage(pageDestination);
    }
  }

  
  public rechercher(recherche: any): void{//On utilise la variable 'recherche' quand on veut filtrer le résultat des traces entre une date de début et une date de fin
    $(".current").attr("class", "paginate_button");
    $("#p1").addClass("current");
    if(recherche == ''){
      this.rechercheActuel = $('#recherche').val();
    }
    else{
      this.rechercheActuel = recherche;
    }
    this.ligneDebut = 0;
    this.pageActuelle = 1;
    this.pageDestination = 1;
    this.ngOnInit();
  }

  ajouter_modifier_Element(element: any): void{
    if(element != 0){
      this.etatModification = true;
      this.elementActuelAModifier = element;
    }
    if(element == 1){
      this.typeElements = new Array();
      this.typeElements.push("GABs");
      this.attributs = new Array();
      this.attributs.push(["Code GAB", "codeGAB", 0, ""], ["Libellé GAB", "libGAB", 0, ""], ["Url GAB", "urlGAB", 0, ""], ["Url GAB archive", "urlGABArchive", 0, ""], ["Date de la dernière remonée", "dateDernierRemontee", 2, ""], ["Libellé burpo", "libelleBurpo", 1, ""]);
    }
    this.referencesAvant = new Array();
    this.referencesApres = new Array();
    $('.add-new').hide();
    this.modeFormulaire = true;
    if(this.typeElements[0] == "regions"){
      this.attributs[0][3] = element.codReg;
      this.attributs[1][3] = element.libReg;
    }
    else if(this.typeElements[0] == "agences"){
      this._regionService.getRegions(0, "", "").subscribe(
        data => {
          this.attributs[0][3] = element.codeBurpo;
          this.attributs[1][3] = element.libelleBurpo;
          var regions = data.regions;
          var indice = 0;
          for(let i = 0; i<regions.length; i++){
            if(indice == 0){
              if(regions[i].codReg == element.codReg){
                this.referenceSelected.push([element.codReg, element.libReg]);
                indice = i;
              }
              else{
                this.referencesAvant.push([regions[i].codReg, regions[i].libReg]);
              }
            }
            else{
              this.referencesApres.push([regions[i].codReg, regions[i].libReg]);
            }
          }
        }
      );

    }
    else if(this.typeElements[0] == "GABs"){
      this._agenceService.getAgences("", 0, "", "").subscribe(
        data => {
          this.attributs[0][3] = element.codeGAB;
          this.attributs[1][3] = element.libGAB;
          this.attributs[2][3] = element.urlGAB;
          this.attributs[3][3] = element.urlGABArchive;
          var agences = data.agences;
          var indice = 0;
          for(let i = 0; i<agences.length; i++){
            if(indice == 0){
              if(agences[i].codeBurpo == element.codeBurpo){
                this.referenceSelected.push([agences[i].codeBurpo, agences[i].libelleBurpo]);
              }
              else{
                this.referencesAvant.push([agences[i].codeBurpo, agences[i].libelleBurpo]);
              }
            }
            else{
              this.referencesApres.push([agences[i].codeBurpo, agences[i].libelleBurpo]);
            }
          }
        }
      );
    }
    else if(this.typeElements[0] == "traces"){
      this._gabService.getGABs("", 0, "", "").subscribe(
        data => {
          var gabs = data.gabs;
          var indice = 0;
          for(let i = 0; i<gabs.length; i++){
            var indice = 0;
            if(indice == 0){
              if(gabs[i].codeGAB == element.codeGAB){
                this.referenceSelected.push([gabs[i].codeGAB, gabs[i].libGAB]);
              }
              else{
                this.referencesAvant.push([gabs[i].codeGAB, gabs[i].libGAB]);
              }
            }
            else{
              this.referencesApres.push([gabs[i].codeGAB, gabs[i].libGAB]);
            }
          }
        }
      );
    }
    else if(this.typeElements[0] == "profils"){
      this.attributs[0][3] = element.codeU;
      this.attributs[1][3] = element.nom;
      this.attributs[2][3] = element.prenom;
      this.attributs[3][3] = element.email;
      this.attributs[4][3] = element.motDePasse;
      this.attributs[5][3] = element.profil;
      if(element.profil == 2){
        this.referencesAvant.push(["1", "Administrateur technique"]);
        this.referenceSelected.push(["2", "Administrateur fonctionnel"]);
        this.referencesApres.push(["3", "Opérateur"]);
      }
      else if(element.profil == 3){
        this.referencesAvant.push(["1", "Administrateur technique"]);
        this.referencesAvant.push(["2", "Administrateur fonctionnel"]);
        this.referenceSelected.push(["3", "Opérateur"]);
      }
      else{
        this.referenceSelected.push(["1", "Administrateur technique"]);
        this.referencesApres.push(["2", "Administrateur fonctionnel"]);
        this.referencesApres.push(["3", "Opérateur"]);
      }
    }
  }
  annulerAjout(): void{
    $('.add-new').show();
    this.modeFormulaire = false;
    $('#formulaireAjout').remove();
  }

  retirerInvalid(event: any): void{
    if(this.typeElements[0] == "traces"){
      if(event == 1){
        $("#erreurChamp1").html("");
        $("#dateDebut").removeClass("is-invalid");
      }
      else if(event == 2){
        $("#erreurChamp2").html("");
        $("#dateFin").removeClass("is-invalid");
      }
    }
    else{
      var id = event.target.id;
      $("#" + id).removeClass("is-invalid");
      $("." + id).children('span').remove();
    }
  }


  confirmer_ajout_modification_element(): void{
    var attrs = Array(this.attributs.length);
    for(let i = 0; i<attrs.length; i++){
      attrs[i] = $("#" + this.attributs[i][1] + "Ajout").val();
    }
    var champVide = false;
    var emailIncorrect = false;
    for(let i = 0; i<attrs.length; i++){
      if(attrs[i] == ""){
        $("#" + this.attributs[i][1] + "Ajout").addClass("is-invalid");
        if($("." + this.attributs[i][1] + "Ajout").children('span').empty()){
          $("." + this.attributs[i][1] + "Ajout").append(this.elementErreur + 'Il faut remplir ce champ' + '</span>');
        }
        $("#" + this.attributs[i][1] + "Ajout").focus();
        champVide = true;
        break;
      }
      if(this.attributs[i][0] == "email"){
        var nbreArobase = 0;
        for(let j = 0; j<attrs[i].length; j++){
          if(attrs[i][j] == "@"){
            nbreArobase++;
          }
        }
        if(nbreArobase != 1){
          emailIncorrect = true;
          $("#" + this.attributs[i][1] + "Ajout").addClass("is-invalid");
          if($("." + this.attributs[i][1] + "Ajout").children('span').empty()){
            $("." + this.attributs[i][1] + "Ajout").append(this.elementErreur + 'Cette forme ne correspond pas à une adrese mail' + '</span>');
          }
        }
      }
    }
    if(!champVide && !emailIncorrect){
      if(this.typeElements[0] == "regions"){
        this._regionService.ajouterRegion(attrs[0], attrs[1], this.etatModification).subscribe(
          data => {
            this.apres_ajout_modification_element(data);
        })
      }
      else if(this.typeElements[0] == "agences"){
        this._agenceService.ajouterAgence(attrs[0], attrs[1], attrs[2], this.etatModification).subscribe(
          data => {
            this.apres_ajout_modification_element(data);
        })
      }
      else if(this.typeElements[0] == "GABs"){
        this._gabService.ajouterGAB(attrs[0], attrs[1], attrs[2], attrs[3], attrs[5], this.etatModification).subscribe(
          data => {
            this.apres_ajout_modification_element(data);
        })
      }
      else if(this.typeElements[0] == "profils"){
        this._utilisateurService.ajouterProfil(this.attributs[0][3] != null? this.attributs[0][3] : "", attrs[1], attrs[2], attrs[3], attrs[4], attrs[5], this.etatModification).subscribe(
          data => {
            this.apres_ajout_modification_element(data);
        })
      }
    }
  }
  apres_ajout_modification_element(data : any): void{
    if(data == 1){
      var numeroChamp = 0;
      let DEJA_UTILISE = 'Ce mot est déja utilisé';
      if(this.typeElements[0] == "profils"){
        numeroChamp = 3;
        DEJA_UTILISE = 'Cette adresse est déjà utilisée';
      }
      $("#" + this.attributs[numeroChamp][1] + "Ajout").addClass("is-invalid");
      $("." + this.attributs[numeroChamp][1] + "Ajout").append(this.elementErreur + DEJA_UTILISE + '</span>');
    }
    else if(data == 0){
      var ttre;
      if(this.etatModification == true){
        ttre = "modifié";
      }
      else{
        ttre = "ajouté";
      }
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: "L'élement a été bien " +ttre,
        showConfirmButton: false,
        timer: 1500
      })
      this.etatModification = false;
      this.modeFormulaire = false;
      this.ligneDebut = 0;
      this._etatTransition = 0;
      this.pageActuelle = 1;
      this.pageDestination = 1;
      this.ngOnInit();
    }
  }
  fermerPageFormulaire(): void{
    this.modeFormulaire = false;
    this.etatModification = false;
  }
  
  recupererTraces(){
    this.modeFormulaire = true;
    var dateDebut = $("#dateDebut").val();
    var dateFin = $("#dateFin").val();
    var codeGAB = $("#champCodeGAB").val();
    if(dateDebut == ""){
      $("#dateDebut").addClass("is-invalid");
      $("#erreurChamp1").html('Il faut remplir ce champ');
    }
    else if(dateFin == ""){
      $("#dateFin").addClass("is-invalid");
      $("#erreurChamp2").html('Il faut remplir ce champ');
    }
    else{
      this._traceService.recupererTraces(codeGAB, dateDebut, dateFin).subscribe(
        data => {
          var messages = data.message;
          var message = "";
          var resultats = new Array(4);
          for(let i = 0; i<4; i++){
            resultats[i] = new Array();
          }
          for(let i = 0; i<messages[0].length; i++){
            if(messages[0][i] == '1'){
              resultats[0].push(messages[1][i]);
            }
            else if(messages[0][i] == '0'){
              resultats[1].push(messages[1][i]);
            }
            else if(messages[0][i] == '2'){
              resultats[2].push(messages[1][i]);
            }
            else if(messages[0][i] == '16'){
              resultats[3].push(messages[1][i]);
            }
          }
          if(resultats[0].length != 0){
            message += "La liste des traces qu'on a récupéré : ";
            for(let i = 0; i<resultats[0].length; i++){
              message += "\n\t" + resultats[0][i];
            }
            message += '\n';
          }
          if(resultats[1].length != 0){
            message += "La liste des traces qui existent déjà dans le serveur central : ";
            for(let i = 0; i<resultats[1].length; i++){
              message += "\n\t" + resultats[1][i];
            }
            message += '\n';
          }
          if(resultats[2].length != 0){
            message += "La liste des traces qu'on n'a pas trouvé : ";
            for(let i = 0; i<resultats[2].length; i++){
              message += "\n\t" + resultats[2][i];
            }
            message += '\n';
          }
          if(resultats[3].length != 0){
            message = "On n'a pas pu accéder au GAB";
          }
          alert(message);
          this.modeFormulaire = false;
          this.ligneDebut = 0;
          this._etatTransition = 0;
          this.pageActuelle = 1;
          this.pageDestination = 1;
          this.ngOnInit();
        }
      )
    }
  }

  typeProfil(profil: number): any{
    if(profil == 1){
      return "Administrateur technique";
    }
    else if(profil == 2){
      return "Administrateur fonctionnel";
    }
    else if(profil == 3){
      return "Opérateur";
    }
  }

  autoriserModification(): void{
    this.modificationAuorisee = !this.modificationAuorisee;
  }

  trierPar(attribut: any): void{
    $(".current").attr("class", "paginate_button");
    $("#p1").addClass("current");
    this.ligneDebut = 0;
    this.pageActuelle = 1;
    this.pageDestination = 1;
    if(this.elementDeTri == attribut){
      this.ascendre = !this.ascendre;
    }
    else{
      this.ascendre = true;
      this.elementDeTri = attribut;
    }
    this.ngOnInit();
  }
  filtrerTraces(): void{
    var dateDebut = $("#dateFiltrageDebut").children("input").val();
    var dateFin = $("#dateFiltrageFin").children("input").val();
    this.rechercher("{" + dateDebut + " " + dateFin + "}");
  }
}
