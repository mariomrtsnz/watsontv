export class CollectionDto {
    name: string;
    description: string;
    owner: string;

    constructor(n: string, d: string, o: string) {
        this.name = n;
        this.description = d;
        this.owner = o;
    }
}
