import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {KeycloakService} from "keycloak-angular";
import {fromPromise} from "rxjs/internal-compatibility";
import {flatMap, map} from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private keycloak: KeycloakService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return fromPromise(this.keycloak.getToken())
        .pipe(
            flatMap(idToken => {
              if (idToken) {
                const cloned = request.clone({
                  headers: request.headers.set("Authorization", "Bearer " + idToken)
                });

                return next.handle(cloned);
              }
              else {
                return next.handle(request);
              }
            })
        );
  }
}
