import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RequestDTO, RequestItem } from "../model/request.model";
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  path = 'http://localhost:8080/api/request/';
  pathGetAll = this.path + "all";

  /*headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    //'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    'Response-Type': 'text'             //<- b/c Angular understands text
  });*/

  constructor(private http: HttpClient) { }

  denyRequest(requestBroj: string): Observable<string>{
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.put<string>(this.path + "deny/" + requestBroj, { headers: headers });
  }

  getRequestsForUser(username: string){
    console.log("getforuser = ", username);
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<Array<RequestItem>>(this.path + username + "/all", { headers: headers })
      .pipe(map(response => response));
  }

  getRequests() {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<Array<RequestItem>>(this.pathGetAll, { headers: headers })
      .pipe(map(response => response));
  }

  deleteRequest(broj: string, updateTable: Function) {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    this.http.delete(this.path + broj, { headers: headers })
      .pipe(map(response => response))
      .subscribe(response => {
        updateTable();
        Swal.fire({
          title: 'Success!',
          text: 'Request successfully deleted!',
          icon: 'success',
          confirmButtonText: 'OK'
        });
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

  addRequest(response: string, success: Function) {
    console.log("service add request = ");
    console.log(response);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', //<- To SEND XML
      'Accept': 'application/xml',       //<- To ask for XML
      'Response-Type': 'text'
    });

    this.http.post<void>(this.path, response, { headers: headers })
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
