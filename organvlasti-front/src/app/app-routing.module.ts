import { Component, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { NoticesComponent } from './components/notices/notices.component';
import { RequestsComponent } from './components/requests/requests.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { AddResponseComponent } from './components/add-response/add-response.component';
import { AddRequestComponent } from './components/add-request/add-request.component';
import { AddDecisionAppealComponent } from './components/add-decision-appeal/add-decision-appeal.component';
import { AddSilenceAppealComponent } from './components/add-silence-appeal/add-silence-appeal.component';
import { AddNoticeComponent } from './components/add-notice/add-notice.component';
import { SilenceAppealsComponent } from './components/silence-appeals/silence-appeals.component';
import { DecisionAppealsComponent } from './components/decision-appeals/decision-appeals.component';
import { AddExplanationComponent } from './components/add-explanation/add-explanation.component';
import { ResponsesComponent } from './components/responses/responses.component';
import { ReportsComponent } from './components/reports/reports.component';
// import { NaprednaPretragaComponent } from './components/napredna-pretraga/napredna-pretraga.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent},
  { path: 'home', component: HomeComponent },
  { path: 'notices', component: NoticesComponent },  
  { path: 'requests', component: RequestsComponent },  
  { path: 'decision-appeals', component: DecisionAppealsComponent },
  { path: 'silence-appeals', component: SilenceAppealsComponent },
//  { path: 'add-response', component: AddResponseComponent },
  { path: 'add-request', component: AddRequestComponent },
//  { path: 'add-decision-appeal', component: AddDecisionAppealComponent },
//  { path: 'add-silence-appeal', component: AddSilenceAppealComponent },
  { path: 'add-notice/:podnosiocUsername/:id', component: AddNoticeComponent },
  { path: 'add-explanation/:broj/:tip/:username', component: AddExplanationComponent },
  { path: 'responses', component: ResponsesComponent },
  { path: 'reports', component: ReportsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
