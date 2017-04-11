import { Component } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';


import { PageXComponent } from './pagex.component';
import { PageYComponent } from './pagey.component';
import { PageService } from './page.service';
import {KeycloakService} from './keycloak/keycloak.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [PageService, KeycloakService]
})
export class AppComponent {
  display: any;

  constructor(private http: Http, private kc: KeycloakService) {
    this.userId = kc.getUserName() 
    this.display = kc.hasRole()
  }
  
  logout() {
    this.kc.logout();
  }

  title = 'OpenID POC';
  userId = ''
}
