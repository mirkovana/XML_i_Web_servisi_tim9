import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SilenceAppealDTO } from "../model/silence-appeal.model";
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class SilenceAppealService {

  path = 'http://localhost:8080';
  /*headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    //'Response-Type': 'text'             //<- b/c Angular understands text
  });*/

  constructor(private http: HttpClient) { }

  addSilenceAppeal(appeal: string, success: Function) {
    console.log("service add silence appeal = ");
    console.log(appeal);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', //<- To SEND XML
      'Accept': 'application/xml',       //<- To ask for XML
      'Response-Type': 'text'
    });
    this.http.post<void>(this.path + '/api/silence-appeals', appeal, {headers: headers})
      .pipe(map(response => response))
      .subscribe(response => {
        Swal.fire({
          title: 'Success!',
          text: 'Add silence appeal successfully!',
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
