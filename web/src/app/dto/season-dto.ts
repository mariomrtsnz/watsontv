export class SeasonDto {
    series: string;
    number: number;

    constructor(s: string, n: number) {
        this.series = s;
        this.number = n;
    }
}
