export class SilenceAppealModel {
    constructor(public text: string) { }
}

export interface SilenceAppealDTO {
    text: string;
}

export interface SAppealItem {
    status: string;
    podnosiocUlica: string;
    podnosiocGrad: string;
    podnosiocIme: string;
    podnosiocPrezime: string;
    podnosiocUsername: string;
    organVlasti: string;
    broj: string;
    mestoSlanja: string;
    datumSlanja: string;
    poverenikUsername: string;
    razlog: string;
}