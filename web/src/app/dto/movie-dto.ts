export class MovieDto {
    title: string;
    genre: string;
    synopsis: string;
    runtime: number;

    constructor(t: string, g: string, s: string, rt: number) {
        this.title = t;
        this.genre = g;
        this.synopsis = s;
        this.runtime = rt;
    }
}
