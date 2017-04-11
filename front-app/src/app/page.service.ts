import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import {KeycloakService} from './keycloak/keycloak.service';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class PageService {
    
    constructor(private http: Http, private kc: KeycloakService) { }

    getX(): Promise<string> { 
        return this.http.get("http://localhost:8080/x")
            .toPromise()
            .then(response => response.text())
    }
    
    getY(): Promise<string> { 
        return this.http.get("http://localhost:8080/y")
            .toPromise()
            .then(response => response.text())
    }
}