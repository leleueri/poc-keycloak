import { Component } from '@angular/core';
import { PageService } from './page.service';

import {KeycloakService} from './keycloak/keycloak.service';

@Component({
  selector: 'page-y',
  templateUrl: './pagey.component.html'
})

export class PageYComponent {
  title = 'Page with Hello From ServiceY';
  message = 'No Message'
  constructor(private pageService: PageService, private kc: KeycloakService) { 
     pageService.getY().then(y => this.message = y)
   }
}
