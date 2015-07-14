#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <float.h>
#include <assert.h>
#include "list.h"
#include "scgl_graph.h"
#include "scgl_vertex.h"
#include "scgl_edge.h"
#include "scgl_algorithms.h"
#include "pqueue.h"


typedef struct pair {
	unsigned int id;
	cost_type dist;
} pair_t;

static pair_t* pair_new(unsigned int id, cost_type_t dist) {
	pair_t *p = (pair_t*) malloc(sizeof(pair_t));
	assert(p != NULL);
	p->id = id;
	p->dist = dist;
	return p;
}

static int cmp(const void *d1, const void *d2) {
	return ((pair_t*)d2)->dist - ((pair_t*)d1)->dist;
}

static int find_data(const void *d1, const void *d2) {
	return (((pair_t*)d1)->id == (*(unsigned int*)d2));
}

static void swap_data(void **d1, void **d2) {
	free(*d1);
	*d1 = *d2;
}

static unsigned int graph_get_vertex_num(const scgl_graph_t *graph, const scgl_vertex_t *vertex) {
	list_head_t *i;
	scgl_vertex_t *u;
	unsigned int j = 0;
	if(graph != NULL || vertex != NULL) {
		list_for_each(i, &graph->vertexes) {
			u = list_entry(i, scgl_vertex_t, owner_list);
			if (vertex == u)
				break;
			++j;
		}
	}
	return j;
}

void
scgl_dijkstra(const scgl_graph_t *graph, scgl_vertex_t *src, unsigned int *p, cost_type_t *d) {
	list_head_t *i;
	scgl_vertex_t *u;
	scgl_edge_t *e;
	pair_t *pair;
	pqueue_t *q = NULL;
	unsigned int n, j, u_i, v_i;
	char *c;

	if (graph != NULL) {
		n = list_count(&graph->vertexes);
		q = pqueue_create(cmp, n);
		u_i = graph_get_vertex_num(graph, src);
		c = (char*) malloc(n);
		assert(c != NULL);
		for (j=0; j<n; ++j) {
			d[j] = cost_max;
			p[j] = j;
			c[j] = 'w';
		}
		c[u_i] = 'g';
		d[u_i] = 0;
		pair = pair_new(u_i, d[u_i]);
		pqueue_enqueue(q, pair);
		while ((pair = pqueue_dequeue(q)) != NULL) {
			u_i = pair->id;
			free(pair);
			u = scgl_graph_get_vertex_at(graph, u_i);
			list_for_each(i, &u->out) {
				e = list_entry(i, scgl_edge_t, from_list);
				v_i = graph_get_vertex_num(graph, e->to);
				if (e->cost + d[u_i] < d[v_i]) {
					d[v_i] = e->cost + d[u_i];
					p[v_i] = u_i;
					if (c[v_i] == 'w') {
						c[v_i] = 'g';
						pair = pair_new(v_i, d[v_i]);
						pqueue_enqueue(q, pair);
					}
					else if (c[v_i] == 'g') {
						pqueue_replace_data(q, &v_i, pair_new(v_i, cost_max), find_data, swap_data);
					}
				}
			}
			c[u_i] = 'b';
		}
		pqueue_destroy(&q);
		free(c);
	}
}
