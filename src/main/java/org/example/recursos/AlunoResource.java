package org.example.recursos;

import org.example.entidades.Aluno;

import com.sun.research.ws.wadl.Response;

import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
public class AlunoResource {

    private List<Aluno> bancoDados;

    public AlunoResource() {
        bancoDados = new ArrayList();
        bancoDados.add(new Aluno(123, "Joaquim", "Ciência da Computação"));
        bancoDados.add(new Aluno(321, "Célia", "Gastronomia"));
        bancoDados.add(new Aluno(198, "Paulo", "Ciência da Computação"));
        bancoDados.add(new Aluno(891, "Camila", "Biomedicina"));
    }

    @GET
    public List<Aluno> getAlunos() {
        return this.bancoDados;
    }

    @POST
    public Aluno createAluno(Aluno aluno) {
        if(aluno.getNome() == null || aluno.getCurso() == null) {
            throw new BadRequestException("Nome e curso devem ser informado");
        }

        for(Aluno a: bancoDados) {
            if (a.getTia() == aluno.getTia()) {
                throw new BadRequestException("Já existe um aluno com este TIA");
            }
        }

        this.bancoDados.add(aluno);
        return aluno;
    }
    
    @DELETE
    @Path("{tia}")
    public Response delete(@PathParam("tia") LongParam tia) {
    	Aluno a = null;
    	for(Aluno aluno: alunos) {
    		if(aluno.getTia() == tia.get()) {
    			a = aluno;
    			break;
    		}
    	}
    	if(a!=null) {
    		alunos.remove(a);
    	}
    	else {
    		throw new WebApplicationException("Aluno com tia= "+ tia.get()+" não encontrado!", 404);
    		    	}
    	return Response.ok().build();
    }
    @PUT
    @Path("{tia}")
    public Aluno update(@PathParam("tia") LongParam tia, Aluno a){
        for(Aluno aluno: alunos){
            if(aluno.getTia() == tia.get()){
                aluno.setNome(a.getNome());
                aluno.setCurso(a.getCurso());
                return aluno;
            }
        }
        return null;
    }

}
