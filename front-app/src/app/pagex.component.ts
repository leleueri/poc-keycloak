import { Component } from '@angular/core';
import { PageService } from './page.service';

import {KeycloakService} from './keycloak/keycloak.service';

@Component({
  selector: 'page-x',
  templateUrl: './pagex.component.html'
})

export class PageXComponent {
  title = 'Page with Hello From ServiceX';
  message = 'No Message'
  constructor(private pageService: PageService, private kc: KeycloakService) { 
    pageService.getX().then(x => this.message = x)
  }
}
