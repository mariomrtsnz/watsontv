export class CollectionDto {
    name: string;
    description: string;
    
    constructor(n: string, d: string) {
        this.name = n;
        this.description = d;
    }
}
