import type { WriterDTO } from "./writers";

export interface PostDTO {
    id: string,
    label: string,
    text: string,
    wroteBy: WriterDTO,
}