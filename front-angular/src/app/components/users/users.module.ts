import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {UsersRouting} from "./users.routing";
import {UsersService} from "./services/users.service";
import {UsersContainerComponent} from "./container/users.container.component";
import {ClarityModule} from "@clr/angular";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [CommonModule, RouterModule,
        ClarityModule, UsersRouting, ReactiveFormsModule],
  declarations: [UsersContainerComponent],
  providers: [UsersService]
})
export class UsersModule {
} 
