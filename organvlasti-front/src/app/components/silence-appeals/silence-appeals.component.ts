import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { SilenceAppealService } from '../../service/silence-appeal.service';
import { SAppealItem } from '../../model/silence-appeal.model';

@Component({
  selector: 'app-silence-appeals',
  templateUrl: './silence-appeals.component.html',
  styleUrls: ['./silence-appeals.component.css']
})
export class SilenceAppealsComponent implements OnInit {

  appeals: SAppealItem[] = [];

  constructor(private service: SilenceAppealService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.getSilenceAppeals();
  }
  
  getSilenceAppeals() {
    if (this.isAdmin()) {
      console.log("getappealsforadmin");
      this.service.getAppeals().subscribe((data: SAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    } 
  }

  sendExplanation(appeal: SAppealItem){
    console.log("sendExplanation = ", appeal);
    this.router.navigate(['/add-explanation/'+appeal.broj+"/silence/"+appeal.podnosiocUsername]);
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
