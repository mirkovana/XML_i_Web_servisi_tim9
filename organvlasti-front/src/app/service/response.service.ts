import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseDTO } from "../model/response.model";

@Injectable({
  providedIn: 'root'
})
export class ResponseService {
  path = 'http://localhost:8080';
  headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token')
  });

  constructor(private http: HttpClient) { }

  addResponse(response: ResponseDTO): Observable<void> {
    console.log("service add response = ");
    console.log(response);
    return this.http.post<void>(this.path + '/api/response', response);
  }
}
