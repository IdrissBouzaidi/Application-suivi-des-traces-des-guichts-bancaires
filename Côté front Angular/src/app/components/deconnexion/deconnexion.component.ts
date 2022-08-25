import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthentificationService } from 'src/app/services/authentification.service';

@Component({
  selector: 'app-deconnexion',
  templateUrl: './deconnexion.component.html',
  styleUrls: ['./deconnexion.component.css']
})
export class DeconnexionComponent implements OnInit {

  constructor(
    private authentificationService: AuthentificationService,
    private router: Router) {

  }

  ngOnInit() {
    this.authentificationService.logOut();
    this.router.navigate(['login']);
  }

}