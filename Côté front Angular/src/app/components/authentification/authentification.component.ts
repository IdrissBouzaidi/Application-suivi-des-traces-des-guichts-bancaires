import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Utilisateur } from 'src/app/models/utilisateur';
import { AuthentificationService } from 'src/app/services/authentification.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-authentification',
  templateUrl: './authentification.component.html',
  styleUrls: ['./authentification.component.css']
})
export class AuthentificationComponent implements OnInit {

  invalidLogin = false;
  messageErreur: string = "";
  utilisateur = new Utilisateur();


  constructor(private _router: Router,
    private _authentificationService: AuthentificationService) { }

  ngOnInit(): void {
    this._authentificationService.logOut();
  }
  validerInformations(){
    this.utilisateur.email = $("#adresse").val();
    this.utilisateur.motDePasse = $("#motDePasse").val();
    if(this.utilisateur.email == ""){
      $("#adresse").addClass("is-invalid");
      $("#erreurChamp1").html('Il faut remplir ce champ');
      $("#adresse").focus();
    }
    else if(this.utilisateur.motDePasse == ""){
      $("#motDePasse").addClass("is-invalid");
      $("#erreurChamp2").html('Il faut remplir ce champ');
      $("#motDePasse").focus();
    }
    this._authentificationService.validerInformations(this.utilisateur).subscribe(
      data => {
        this.utilisateur = data;
        if(this.utilisateur.profil == -2){
          $("#adresse").addClass("is-invalid");
          $("#erreurChamp1").html("L'email saisi est incoorect");
          $("#adresse").focus();
        }
        else if(this.utilisateur.profil == -1){
          $("#motDePasse").addClass("is-invalid");
          $("#erreurChamp2").html('Le mot de passe saisi est incorrect');
          $("#motDePasse").focus();
        }
        else if(this.utilisateur.profil > 0){
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: "L'authentification a réussi",
            showConfirmButton: false,
            timer: 1500
          })
          let currentUrl = this._router.url;
          this._router.routeReuseStrategy.shouldReuseRoute = () => false;
          this._router.onSameUrlNavigation = 'reload';
          this._router.navigate([currentUrl]);
          sessionStorage.setItem('email', this.utilisateur.email);
          sessionStorage.setItem('profil', this.utilisateur.profil.toString());
          sessionStorage.setItem('nom', this.utilisateur.nom);
          sessionStorage.setItem('prenom', this.utilisateur.prenom);
          this._router.navigateByUrl("/regions");
          this.invalidLogin = false
        }
      }
    )
  }

  
  retirerInvalid(champ: any): void{
    if(champ == 1){
      $("#erreurChamp1").html("");
      $("#adresse").removeClass("is-invalid");
    }
    else if(champ == 2){
      $("#erreurChamp2").html("");
      $("#motDePasse").removeClass("is-invalid");
    }
  }
}