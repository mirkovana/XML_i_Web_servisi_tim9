export class ResponseModel {
    constructor(public text: string) { }
}

export interface ResponseDTO {
    text: string;
}

export interface ResponseItem {
    broj: string;
    podnosiocUsername: string;    
    poverenikIme: string;
    poverenikPrezime: string;
    status: string;
    datum: string;
}