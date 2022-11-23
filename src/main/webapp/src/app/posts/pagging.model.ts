export interface Pagging<T> {
    offset: number;
    items: T[];
}