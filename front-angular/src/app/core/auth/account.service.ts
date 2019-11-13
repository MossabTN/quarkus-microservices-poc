import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import {KeycloakService} from "keycloak-angular";
import {Account} from "./account.model";


@Injectable({ providedIn: 'root' })
export class AccountService {
  private userIdentity: Account;
  private authenticationState = new Subject<any>();

  constructor(private http: HttpClient, private keycloakService: KeycloakService) {}
/*

  fetch(): Observable<HttpResponse<Account>> {
    return this.http.get<Account>(SERVER_API_URL + 'api/account', { observe: 'response' });
  }

  save(account: any): Observable<HttpResponse<any>> {
    return this.http.post(SERVER_API_URL + 'api/account', account, { observe: 'response' });
  }
*/

  hasAnyAuthority(authorities: string[]): boolean {
    if (!this.keycloakService.isLoggedIn() || !this.userIdentity) {
      return false;
    }

    for (let i = 0; i < authorities.length; i++) {
      if (this.keycloakService.isUserInRole(authorities[i])) {
        return true;
      }
    }

    return false;
  }

  hasAuthority(authority: string): Promise<boolean> {
    if (!this.keycloakService.isLoggedIn()) {
      return Promise.resolve(false);
    }

    return Promise.resolve(this.keycloakService.isUserInRole(authority));
  }

  identity(force: boolean = false): Promise<Account> {
    // retrieve the userIdentity data from the server, update the identity object, and then resolve.
    return this.keycloakService.loadUserProfile(force)
      .then(response => {
        this.userIdentity = response;
        this.authenticationState.next(this.userIdentity);
        return this.userIdentity;
      })
      .catch(err => {
        this.userIdentity = null;
        this.authenticationState.next(this.userIdentity);
        return null;
      });
  }

  isAuthenticated(): Promise<boolean> {
    return this.keycloakService.isLoggedIn();
  }

  isIdentityResolved(): boolean {
    return this.userIdentity !== undefined;
  }

  getAuthenticationState(): Observable<any> {
    return this.authenticationState.asObservable();
  }
  getImageUrl(): string {
    return this.isIdentityResolved() ? this.userIdentity.attributes.imageUrl : null;
  }
}
