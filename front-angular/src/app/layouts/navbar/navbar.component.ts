import {Component, OnInit} from '@angular/core';
import {KeycloakProfile} from "keycloak-js";
import {KeycloakService} from "keycloak-angular";
import {NotificationService} from "../../core/notification/notification.service";
import {PaginationArgs} from "../../shared/pagination.args";
import {Notification} from "../../core/notification/notification.model";


@Component({
  selector: 'app-nav',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit {
  notifications : Notification[] = [];
  userDetails: KeycloakProfile;
  paginationArgs: PaginationArgs = { pageNumber: 0, pageSize: 2, sorts: [] };

  constructor(private keycloakService: KeycloakService, private notificationService: NotificationService) {}

  async ngOnInit() {
    this.notificationService.getAll(this.paginationArgs)
        .subscribe(value => {
            this.notifications = value;
        })

    this.notificationService.notificationState.asObservable()
        .subscribe(value => {
          console.log(value);
          this.notifications.unshift(value)
        })

    if (await this.keycloakService.isLoggedIn()) {
      this.userDetails = await this.keycloakService.loadUserProfile();
    }
  }

  onNotificationMenuClick(){
    console.log('hhhh');
  }

  async doLogout() {
    await this.keycloakService.logout();
  }

  hasSeenNotification(): boolean{
    return this.notifications.some(value => value.seen == false);
  }
}
