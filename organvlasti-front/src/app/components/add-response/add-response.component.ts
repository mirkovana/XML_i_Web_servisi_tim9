import { Component, OnInit, Input } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { ResponseService } from '../../service/response.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ResponseDTO } from "../../model/response.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-response',
  templateUrl: './add-response.component.html',
  styleUrls: ['./add-response.component.css']
})
export class AddResponseComponent implements OnInit {
  zalbaSpec = '';
  textArea: string;

  constructor(private responseService: ResponseService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.zalbaSpec = '<res:zalba xmlns="http://www.projekat.org/resenje"'
                                + ' xmlns:pred="http://www.projekat.org/predicate/"'
                                + ' xmlns:res="http://www.projekat.org/resenje"></res:zalba>';
    
    let xonomyElement = document.getElementById("response");
    Xonomy.render(this.zalbaSpec, xonomyElement, XonomyModel.zalbaXonomy);
  }

  sendFile() {
    console.log("sendFile");
    
    let obj: ResponseDTO = {
      "text" : this.textArea
    };
    console.log(obj);
    this.responseService.addResponse(obj).subscribe(
      response => {
        console.log("added response");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );
  }
}
