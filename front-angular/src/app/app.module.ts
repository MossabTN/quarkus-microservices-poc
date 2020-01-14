import {BrowserModule} from '@angular/platform-browser';
import {NgModule, DoBootstrap} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ClarityModule} from '@clr/angular';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';

import {AppRoutingModule} from './app-routing.module';

import {environment} from '../environments/environment';
import {AppComponent} from "./app.component";
import {UsersContainerComponent} from "./components/users/container/users.container.component";
import {HomeComponent} from "./home/home.component";
import {UsersService} from "./components/users/services/users.service";
import {AuthInterceptor} from "./core/interceptor/auth.interceptor";
import {FooterComponent, NavbarComponent} from "./layouts";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

let keycloakService: KeycloakService = new KeycloakService();

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        FooterComponent,
        HomeComponent],
    imports: [
        BrowserModule,
        HttpClientModule,
        BrowserAnimationsModule,
        ClarityModule,
        KeycloakAngularModule,
        AppRoutingModule
    ],
    providers: [
        UsersService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: KeycloakService,
            useValue: keycloakService
        }
    ],
    entryComponents: [AppComponent]
})
export class AppModule implements DoBootstrap {
    async ngDoBootstrap(app) {
        const {keycloak} = environment;

        try {
            await keycloakService.init({config: keycloak});
            app.bootstrap(AppComponent);
        } catch (error) {
            console.error('Keycloak init failed', error);
        }
    }
}
