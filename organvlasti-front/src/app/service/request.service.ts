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
  pathSearch = this.path + "search";
  pathSearchKeywords = this.path + "keywords";

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

  /*downloadRequestFile(broj: string): Observable<any> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    //@ts-ignore
    return this.http.get<any>(this.path + "download/file/" + broj, { headers: headers, responseType: 'text' });
  }*/

  getRequestsForUser(username: string): Observable<RequestItem[]>{
    console.log("getforuser = ", username);
    const headers = new HttpHeaders({ 
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
    });
    return this.http.get<string>(this.path + username + "/all", { headers: headers,
                                                                 responseType: 'text' as 'json' })
      .pipe(map((xml: string) => this.xmlToRequests(xml)));
  }

  getRequests(): Observable<RequestItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.pathGetAll, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToRequests(xml)));
  }


  private xmlToRequests(xml: string): RequestItem[] {
    //console.log("parse = ", xml);
    let requestItems: RequestItem[] = [];
    const parser = new DOMParser();
    let requestsList = parser.parseFromString(xml,"text/xml").getElementsByTagName('requestItem'); 
    //console.log(requestsList);
    for (let i = 0; i < requestsList.length; ++i){
      //console.log(requestsList.item(i).getElementsByTagName('broj')[0].textContent);
      //console.log(requestsList.item(i).getElementsByTagName('status')[0].textContent);
      requestItems.push({
        'broj': requestsList.item(i).getElementsByTagName('broj')[0].textContent,
        'datum': requestsList.item(i).getElementsByTagName('datum')[0].textContent,
        'institucija': requestsList.item(i).getElementsByTagName('institucija')[0].textContent,
        'username': requestsList.item(i).getElementsByTagName('username')[0].textContent,
        'time': requestsList.item(i).getElementsByTagName('time')[0].textContent,
        'status': requestsList.item(i).getElementsByTagName('status')[0].textContent
      }) 
    }
    return requestItems;
  }
  
  deleteRequest(broj: string, updateTable: Function) {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    this.http.delete(this.path + broj, { headers: headers })
      .pipe(map(response => response))
      .subscribe(response => {
        updateTable();
        Swal.fire({
          title: 'Success!',
          text: 'Zahtev obrisan!',
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
  
  searchByKeywords(xml: string): Observable<RequestItem[]>{
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml',
      'Accept': 'application/xml',      
      'Response-Type': 'text'
    });
    return this.http.post<string>(this.pathSearchKeywords, xml, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToRequests(xml)));
  }

  searchByMetadata(xml: string): Observable<RequestItem[]>{
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', //<- To SEND XML
      'Accept': 'application/xml',       //<- To ask for XML
      'Response-Type': 'text'
    });
    return this.http.post<string>(this.pathSearch, xml, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToRequests(xml)));
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
          text: 'Zahtev poslat!',
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
