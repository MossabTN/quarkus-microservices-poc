import {ModuleWithProviders} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {UsersContainerComponent} from "./container/users.container.component";

export const userRoute: Route[] = [
  {
    path: '',
    component: UsersContainerComponent
  }
]

export const UsersRouting: ModuleWithProviders = RouterModule.forChild(userRoute);
