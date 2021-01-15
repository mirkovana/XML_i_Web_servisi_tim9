import { Component, OnInit } from '@angular/core';
import { RequestItem } from 'src/app/model/request.model';
import { RequestService } from '../../service/request.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  requests: Array<RequestItem> = [];

  constructor(private service: RequestService,
              private userService: UserService) { }

  ngOnInit(): void {
    console.log("get requests");
    this.getRequests();
  }

  getRequests(){
    if(this.userService.isUser()){
      console.log("isUser");
      this.service.getRequestsForUser(this.getUsername()).subscribe((data: any)  => {
        console.log(data);
        this.requests = data;
      }, error => {
        console.log(error);
      });
    }else if(this.userService.isAdmin()){
      //console.log("isAdmin");
      this.service.getRequests().subscribe((data: any)  => {
        console.log(data);
        this.requests = data;
      }, error => {
        console.log(error);
      });;
    }
  }

  replyRequest(request: RequestItem){
    console.log("reply = ", request);
  }

  denyRequest(request: RequestItem){
    console.log("deny = ", request);
    this.service.denyRequest(request.broj).subscribe((data: any)  => {
      console.log(data);
      this.getRequests();
    }, error => {
      console.log(error);
    });
  }

  deleteRequest(request: RequestItem){
    console.log("delete = ", request);
    this.service.deleteRequest(request.broj, ()=> {
      this.requests = this.requests.filter(item => item.broj != request.broj);
    });
  }

  isUser(){
    return this.userService.isUser();
  }
  
  isAdmin(){
    return this.userService.isAdmin();
  }

  getUsername(){
    return this.userService.getUsername();
  }
}
