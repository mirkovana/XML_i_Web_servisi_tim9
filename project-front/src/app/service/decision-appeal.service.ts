import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DecisionAppealDTO } from "../model/decision-appeal.model";

@Injectable({
  providedIn: 'root'
})
export class DecisionAppealService {
  path = 'http://localhost:8080';
  headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token')
  });

  constructor(private http: HttpClient) { }

  addDecisionAppeal(appeal: DecisionAppealDTO): Observable<void> {
    console.log("service add decision appeal = ");
    console.log(appeal);
    return this.http.post<void>(this.path + '/api/decision-appeals', appeal);
  }
}
