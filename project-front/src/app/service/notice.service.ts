import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DecisionAppealDTO } from "../model/decision-appeal.model";
import { NoticeDTO } from '../model/notice.model';


@Injectable({
  providedIn: 'root'
})

export class NoticeService {
  path = 'http://localhost:8080';
  headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token')
  });
  constructor(private http: HttpClient) { }

  addNotice(notice: NoticeDTO): Observable<void> {
    console.log("service add notice = ");
    console.log(notice);
    return this.http.post<void>(this.path + '/api/notice', notice);
  }

  getHTML(){
    return this.http.get<void>(this.path + '/api/notice/html/123abc');
  }
}
