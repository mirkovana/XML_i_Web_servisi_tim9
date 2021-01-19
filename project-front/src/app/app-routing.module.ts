import { Component, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { AddResponseComponent } from './components/add-response/add-response.component';
import { AddDecisionAppealComponent } from './components/add-decision-appeal/add-decision-appeal.component';
import { AddSilenceAppealComponent } from './components/add-silence-appeal/add-silence-appeal.component';
import { SilenceAppealsComponent } from './components/silence-appeals/silence-appeals.component';
import { DecisionAppealsComponent } from './components/decision-appeals/decision-appeals.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent},
  { path: 'home', component: HomeComponent },
  { path: 'add-response', component: AddResponseComponent },
  { path: 'add-decision-appeal', component: AddDecisionAppealComponent },
  { path: 'add-silence-appeal', component: AddSilenceAppealComponent },
  { path: 'decision-appeals', component: DecisionAppealsComponent },
  { path: 'silence-appeals', component: SilenceAppealsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
