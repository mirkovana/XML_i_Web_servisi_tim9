import { Component, OnInit, Input } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { RequestService } from '../../service/request.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RequestDTO } from "../../model/request.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-request',
  templateUrl: './add-request.component.html',
  styleUrls: ['./add-request.component.css']
})
export class AddRequestComponent implements OnInit {
  zahtevSpec = '';
  textArea: string;

  constructor(private requestService: RequestService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  sendFile() {
    console.log("sendFile");
    
    let obj: RequestDTO = {
      "text" : this.textArea
    };
    console.log(obj);
    this.requestService.addRequest(obj).subscribe(
      response => {
        console.log("added request");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );
  }
}
