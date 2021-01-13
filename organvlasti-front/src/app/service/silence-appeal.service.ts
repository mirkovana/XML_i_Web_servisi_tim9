import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SilenceAppealDTO } from "../model/silence-appeal.model";

@Injectable({
  providedIn: 'root'
})
export class SilenceAppealService {

  path = 'http://localhost:8080';
  headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token')
  });

  constructor(private http: HttpClient) { }

  addSilenceAppeal(appeal: SilenceAppealDTO): Observable<void> {
    console.log("service add silence appeal = ");
    console.log(appeal);
    return this.http.post<void>(this.path + '/api/silence-appeals', appeal);
  }
}
