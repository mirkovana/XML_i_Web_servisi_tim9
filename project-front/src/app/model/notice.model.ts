export class NoticeModel {
    constructor(public text: string) { }
}

export interface NoticeDTO {
    text: string;
}

export interface NoticeItem{
    broj: string;
    username: string;
    datum: string;
    organVlastiUsername: string;
    nazivOrgana: string;
    sedisteOrgana: string;
    imePodnosioca: string;
    prezimePodnosioca: string;
    iznos: string; 
}