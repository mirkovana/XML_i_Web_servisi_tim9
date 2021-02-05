import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { FormsModule, NgSelectOption } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { CommonModule } from "@angular/common";
import { NgSelectModule } from "@ng-select/ng-select";
import { MatListModule } from "@angular/material/list";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatCardModule } from '@angular/material/card';

import { UserService } from './service/user.service';
import { ResponseService } from './service/response.service';
import { RequestService } from './service/request.service';
import { InterceptorService } from './service/interceptor.service'
import { DecisionAppealService } from './service/decision-appeal.service';
import { SilenceAppealService } from './service/silence-appeal.service';

import { SignUpComponent } from './components/sign-up/sign-up.component';
import { AddResponseComponent } from './components/add-response/add-response.component';
import { AddRequestComponent } from './components/add-request/add-request.component';
import { AddDecisionAppealComponent } from './components/add-decision-appeal/add-decision-appeal.component';
import { AddSilenceAppealComponent } from './components/add-silence-appeal/add-silence-appeal.component';
import { AddNoticeComponent } from './components/add-notice/add-notice.component';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { RequestsComponent } from './components/requests/requests.component';
import { NoticesComponent } from './components/notices/notices.component';
import { SilenceAppealsComponent } from './components/silence-appeals/silence-appeals.component';
import { DecisionAppealsComponent } from './components/decision-appeals/decision-appeals.component';
import { AddExplanationComponent } from './components/add-explanation/add-explanation.component';
import { ResponsesComponent } from './components/responses/responses.component';
import { ReportsComponent } from './components/reports/reports.component';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SignUpComponent,
    AddResponseComponent,
    AddRequestComponent,
    AddDecisionAppealComponent,
    AddSilenceAppealComponent,
    AddNoticeComponent,
    NavigationBarComponent,
    RequestsComponent,
    NoticesComponent,
    SilenceAppealsComponent,
    DecisionAppealsComponent,
    AddExplanationComponent,
    ResponsesComponent,
    ReportsComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatCardModule,
    MatIconModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatButtonModule,
    MatDialogModule,
    CommonModule,
    NgSelectModule,
    MatMenuModule,
    MatListModule,
    MatExpansionModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: InterceptorService,
    multi: true
  },
    UserService,
    ResponseService,
    RequestService,
    DecisionAppealService,
    SilenceAppealService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
