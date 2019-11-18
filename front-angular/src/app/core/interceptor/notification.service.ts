import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Subject} from 'rxjs';
import {KeycloakService} from "keycloak-angular";


@Injectable({ providedIn: 'root' })
export class NotificationService {
  private notificationState = new Subject<any>();

  constructor(private http: HttpClient, private keycloakService: KeycloakService) {

  }

}
