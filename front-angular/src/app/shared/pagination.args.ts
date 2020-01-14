export interface PaginationArgs {
  pageNumber?: number;
  pageSize?: number;
  sorts?: PaginationSortArgs[];
}

export interface PaginationSortArgs {
  property: string;
  direction: PaginationSortOrderType;
}

type PaginationSortOrderType = 'ASC' | 'DESC';

export interface PaginationPage<T> {
  content: T[];
  last?: boolean;
  totalElements?: number;
  totalPages?: number;
  size?: number;
  number?: number;
  first?: boolean;
  sort?: PaginationSortArgs[];
} 
