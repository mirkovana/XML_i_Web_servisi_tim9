import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { ResponseItem } from '../../model/response.model';
import { ResponseService } from '../../service/response.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-responses',
  templateUrl: './responses.component.html',
  styleUrls: ['./responses.component.css']
})
export class ResponsesComponent implements OnInit {

  responses: ResponseItem[] = [];

  constructor(private service: ResponseService,
    private userService: UserService,
    private router: Router) { }

    ngOnInit(): void {
      this.getResponses();
    }
  
    getResponses() {
      if (this.isUser()) {
        console.log("getappealsforuser");
        this.service.getResponsesForUsername(this.getUsername()).subscribe((data: ResponseItem[]) => {
          console.log("component subscribe = ", data);
          this.responses = data;
        }, error => {
          console.log("error = ", error);
        });
      }else{
        console.log("getappealsforadmin");
        this.service.getResponses().subscribe((data: ResponseItem[]) => {
          console.log("component subscribe = ", data);
          this.responses = data;
        }, error => {
          console.log("error = ", error);
        });
      }
    }

    isUser() {
      return this.userService.isUser();
    }
  
    isAdmin() {
      return this.userService.isAdmin();
    }
  
    getUsername() {
      return this.userService.getUsername();
    }
}
