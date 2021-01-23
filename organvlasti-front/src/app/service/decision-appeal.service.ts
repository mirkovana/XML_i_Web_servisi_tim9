import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DecisionAppealDTO } from "../model/decision-appeal.model";
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class DecisionAppealService {
  path = 'http://localhost:8000';
  headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    //'Response-Type': 'text'             //<- b/c Angular understands text
  });

  constructor(private http: HttpClient) { }

  addDecisionAppeal(appeal: string, success: Function){
    console.log("service add decision appeal = ");
    console.log(appeal);
    this.http.post<void>(this.path + '/api/decision-appeals', appeal, {headers: this.headers})
    .pipe(map(response => response))
    .subscribe(response => {
      Swal.fire({
        title: 'Success!',
        text: 'Add decision appeal successfully!',
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
    });
  }
}
