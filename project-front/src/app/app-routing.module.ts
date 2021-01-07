import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { AddResponseComponent } from './components/add-response/add-response.component';
import { AddRequestComponent } from './components/add-request/add-request.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent},
  { path: 'home', component: HomeComponent },
  { path: 'add-response', component: AddResponseComponent },
  { path: 'add-request', component: AddRequestComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
