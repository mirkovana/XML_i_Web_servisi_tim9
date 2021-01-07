import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RequestDTO } from "../model/request.model";

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  path = 'http://localhost:8080';
  headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token')
  });

  constructor(private http: HttpClient) { }

  addRequest(response: RequestDTO): Observable<void> {
    console.log("service add request = ");
    console.log(response);
    return this.http.post<void>(this.path + '/api/request', response);
  }
}
