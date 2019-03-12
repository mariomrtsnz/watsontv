export class SeriesDto {
    title: string;
    coverImage: string;
    genre: string;
    synopsis: string;
    broadcaster: string;

    constructor(t: string, cI: string, g: string, s: string, b: string) {
        this.title = t;
        this.coverImage = cI;
        this.genre = g;
        this.synopsis = s;
        this.broadcaster = b;
    }
}
