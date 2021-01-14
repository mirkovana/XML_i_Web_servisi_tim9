import { Component, OnInit } from '@angular/core';
import { RequestItem } from 'src/app/model/request.model';
import { RequestService } from '../../service/request.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  requests: Array<RequestItem> = [];

  constructor(private service: RequestService) { }

  ngOnInit(): void {
    console.log("get requests");
    this.service.getRequests().subscribe((data: any)  => {
      console.log(data);
      this.requests = data;
    }, error => {
      console.log(error);
    });;
  }

  deleteRequest(request: RequestItem){
    console.log("delete = ", request);
    this.service.deleteRequest(request.broj, ()=> {
      this.requests = this.requests.filter(item => item.broj != request.broj);
    });

  }
}
