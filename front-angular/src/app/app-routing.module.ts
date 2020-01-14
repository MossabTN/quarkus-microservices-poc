import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppAuthGuard} from './core/auth/app.authguard';
import {HomeComponent} from "./home/home.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AppAuthGuard]
  },
  {
    path: 'users',
    loadChildren: () => import('./components/users/users.module').then(m => m.UsersModule),
    canActivate: [AppAuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AppAuthGuard]
})
export class AppRoutingModule {}
