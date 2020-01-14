import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {KeycloakService} from "keycloak-angular";
import * as  EventBus from 'vertx3-eventbus-client';
import {Observable, Subject} from "rxjs";
import {PaginationArgs} from "../../shared/pagination.args";
import {NotificationConfig} from "./notification.config";
import {Notification} from "./notification.model";


@Injectable({ providedIn: 'root' })
export class NotificationService {
  public notificationState = new Subject<any>();

  private eb;

  constructor(private http: HttpClient, private keycloakService: KeycloakService) {
    this.connectWS();
  }

  async connectWS() {
    const self = this;
    const token = await this.keycloakService.getToken();
    self.eb = new EventBus(NotificationConfig.ws + '?access-token=' + token);
    self.eb.onopen = function () {
      self.eb.registerHandler('notification', function (error, message) {
        self.notificationState.next(JSON.parse(message.body))
      });

      self.eb.send('in', {fruit: 'apple', color: 'red'});
      self.eb.publish('in', {fruit: 'grape', color: 'yellow'});
    };
    self.eb.onclose = (e) => {
      //console.log(e);
      setTimeout(()=> this.connectWS(), 1000);
    };
    //self.eb.enableReconnect(true);
  }

  sendWS(msg){
    this.eb.send('in', msg);
  }

  getAll(paginationArgs: PaginationArgs): Observable<Notification[]> {
    let params = new HttpParams();
    params = params.set('size', `${paginationArgs.pageSize}`);
    params = params.set('page', `${paginationArgs.pageNumber}`);
    return this.http.get<Notification[]>(NotificationConfig.api,{params});
  }
}
