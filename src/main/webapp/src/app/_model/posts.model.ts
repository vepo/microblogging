export interface CreatePostRequest {
    content: string;
}

export interface Post {
    id: number;
    content: string;
    createdAt: Date;
}