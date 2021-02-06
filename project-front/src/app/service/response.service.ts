import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseItem } from "../model/response.model";
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class ResponseService {
  path = 'http://localhost:8070/api/response/';
  pathGetAll = this.path + "all";
  pathSearch = this.path + "search";
  pathSearchKeywords = this.path + "keywords";

  /*headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    //'Response-Type': 'text'             //<- b/c Angular understands text
  });*/

  constructor(private http: HttpClient) { }

  addResponse(response: string, tip: string, success: Function) {
    console.log("service add response = ", tip);
    console.log(response);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', //<- To SEND XML
      'Accept': 'application/xml',       //<- To ask for XML
      'Response-Type': 'text'
    });

    this.http.post<void>(this.path + tip , response, {headers: headers})
      .pipe(map(response => response))
      .subscribe(response => {
        Swal.fire({
          title: 'Success!',
          text: 'Resenje uspesno poslato!',
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

  getResponses(): Observable<ResponseItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.pathGetAll, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToResponse(xml)));
  }

  getResponsesForUsername(username: string): Observable<ResponseItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.path + username + "/all", { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToResponse(xml)));
  }

  searchByKeywords(xml: string): Observable<ResponseItem[]> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml',
      'Accept': 'application/xml',      
      'Response-Type': 'text'
    });
    return this.http.post<string>(this.pathSearchKeywords, xml, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToResponse(xml)));
  }

  searchByMetadata(xml: string): Observable<ResponseItem[]>{
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', //<- To SEND XML
      'Accept': 'application/xml',       //<- To ask for XML
      'Response-Type': 'text'
    });
    return this.http.post<string>(this.pathSearch, xml, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToResponse(xml)));
  }
  
  private xmlToResponse(xml: string): ResponseItem[] {
    console.log("parse = ", xml);
    let appealItems: ResponseItem[] = [];
    const parser = new DOMParser();
    let responseList = parser.parseFromString(xml,"text/xml").getElementsByTagName('responseItem'); 
    console.log("responseList = ", responseList);
    for (let i = 0; i < responseList.length; ++i){
      appealItems.push({
        'broj': responseList.item(i).getElementsByTagName('broj')[0].textContent,
        'podnosiocUsername': responseList.item(i).getElementsByTagName('podnosiocUsername')[0].textContent,
        'poverenikIme': responseList.item(i).getElementsByTagName('poverenikIme')[0].textContent,
        'poverenikPrezime': responseList.item(i).getElementsByTagName('poverenikPrezime')[0].textContent,
        'status': responseList.item(i).getElementsByTagName('status')[0].textContent,
        'datum': responseList.item(i).getElementsByTagName('datum')[0].textContent,
      }) 
    }
    return appealItems;
  }
}
