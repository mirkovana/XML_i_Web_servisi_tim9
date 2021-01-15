import { Component, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { AddResponseComponent } from './components/add-response/add-response.component';
import { AddRequestComponent } from './components/add-request/add-request.component';
import { AddDecisionAppealComponent } from './components/add-decision-appeal/add-decision-appeal.component';
import { AddSilenceAppealComponent } from './components/add-silence-appeal/add-silence-appeal.component';
import { AddNoticeComponent } from './components/add-notice/add-notice.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent},
  { path: 'home', component: HomeComponent },
  { path: 'add-response', component: AddResponseComponent },
  { path: 'add-request', component: AddRequestComponent },
  { path: 'add-decision-appeal', component: AddDecisionAppealComponent },
  { path: 'add-silence-appeal', component: AddSilenceAppealComponent },
  { path: 'add-notice/:id', component: AddNoticeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
