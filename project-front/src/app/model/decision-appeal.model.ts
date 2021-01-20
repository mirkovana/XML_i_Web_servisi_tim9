export class DecisionAppealModel {
    constructor(public text: string) { }
}

export interface DecisionAppealDTO {
    text: string;
}

export interface DAppealItem {
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
}