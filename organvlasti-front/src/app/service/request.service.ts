import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RequestDTO } from "../model/request.model";
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  path = 'http://localhost:8080';
  headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    //'Response-Type': 'text'             //<- b/c Angular understands text
  });

  constructor(private http: HttpClient) { }

  addRequest(response: string, success: Function){
    console.log("service add request = ");
    console.log(response);      
    this.http.post<void>(this.path + '/api/request', response, {headers: this.headers})
    .pipe(map(response => response))
    .subscribe(response => {
      Swal.fire({
        title: 'Success!',
        text: 'Add request successfully!',
        icon: 'success',
        confirmButtonText: 'OK'
      });
      success();
      return true;
    }, error => {
      console.log(error);
      Swal.fire({
        title: 'Error!',
        text: 'Something went wrong! ' + error.error,
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    })

  }
}
