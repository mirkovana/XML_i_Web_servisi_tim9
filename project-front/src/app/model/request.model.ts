export class RequestModel {
    constructor(public text: string) { }
}

export interface RequestDTO {
    text: string;
}

export interface RequestItem{
    broj: string,
    datum: string,
    institucija: string,
    username: string,
    time: string,
    status: string
}