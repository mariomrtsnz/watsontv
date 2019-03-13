export class EpisodeDto {
    name: string;
    synopsis: string;
    airTime: Date;
    duration: number;
    number: number;
    season: string;

    constructor(n: string, s: string, aT: Date, d: number, num: number, sea: string) {
        this.name = n;
        this.synopsis = s;
        this.airTime = aT;
        this.duration = d;
        this.number = num;
        this.season = sea;
    }
}
