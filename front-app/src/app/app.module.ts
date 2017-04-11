import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule }   from '@angular/router';

import { AppComponent } from './app.component';
import { PageXComponent } from './pagex.component';
import { PageYComponent } from './pagey.component';

import { KeycloakService } from './keycloak/keycloak.service';
import { KeycloakHttp, KEYCLOAK_HTTP_PROVIDER } from './keycloak/keycloak.http';

@NgModule({
  declarations: [
    AppComponent, 
    PageYComponent,
    PageXComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
      {
        path: 'front-app',
        component: AppComponent
      },
      {
        path: 'front-app/page-x',
        component: PageXComponent
      },
      {
        path: 'front-app/page-y',
        component: PageYComponent
      }
    ])
  ],
  providers: [
    KeycloakService,
    KEYCLOAK_HTTP_PROVIDER
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
