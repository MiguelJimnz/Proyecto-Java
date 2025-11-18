package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Empleado;
import com.example.Proyecto.Model.HorasTrabajadas;
import com.example.Proyecto.Model.Pedido;
import com.example.Proyecto.Model.TipoTarea;
import com.example.Proyecto.Repository.EmpleadoRepository;
import com.example.Proyecto.Repository.HorasTrabajadasRepository;
import com.example.Proyecto.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class HorasTrabajadasService {

    @Autowired
    private HorasTrabajadasRepository horasRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public HorasTrabajadas registrarHoras(HorasTrabajadas horas) {
        // Validar que el empleado existe
        Empleado empleado = empleadoRepository.findById(horas.getEmpleado().getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + horas.getEmpleado().getEmpleadoId()));

        // Validar pedido si está presente
        if (horas.getPedido() != null && horas.getPedido().getPedidoId() != null) {
            Pedido pedido = pedidoRepository.findById(horas.getPedido().getPedidoId())
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + horas.getPedido().getPedidoId()));
            horas.setPedido(pedido);
        }

        // Validar que la fecha no sea futura
        if (horas.getFecha().isAfter(LocalDate.now())) {
            throw new RuntimeException("No se pueden registrar horas para fechas futuras");
        }

        // Validar que no haya superposición de horarios
        validarSuperposicionHoraria(horas);

        horas.setEmpleado(empleado);
        return horasRepository.save(horas);
    }

    private void validarSuperposicionHoraria(HorasTrabajadas nuevasHoras) {
        List<HorasTrabajadas> horasExistentes = horasRepository.findByEmpleadoEmpleadoIdAndFechaBetween(
                nuevasHoras.getEmpleado().getEmpleadoId(),
                nuevasHoras.getFecha(),
                nuevasHoras.getFecha()
        );

        for (HorasTrabajadas existente : horasExistentes) {
            if (existeSuperposicion(nuevasHoras, existente)) {
                throw new RuntimeException("El empleado ya tiene horas registradas en ese horario");
            }
        }
    }

    private boolean existeSuperposicion(HorasTrabajadas h1, HorasTrabajadas h2) {
        return !(h1.getHoraFin().isBefore(h2.getHoraInicio()) ||
                h1.getHoraInicio().isAfter(h2.getHoraFin()));
    }

    public List<HorasTrabajadas> obtenerHorasPorEmpleado(Long empleadoId) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new RuntimeException("Empleado no encontrado");
        }
        return horasRepository.findByEmpleadoEmpleadoId(empleadoId);
    }

    public List<HorasTrabajadas> obtenerHorasNoVerificadas() {
        return horasRepository.findHorasNoVerificadas();
    }

    public HorasTrabajadas verificarHoras(Long idHora) {
        HorasTrabajadas horas = horasRepository.findById(idHora)
                .orElseThrow(() -> new RuntimeException("Horas no encontradas con ID: " + idHora));
        horas.setVerificado(true);
        return horasRepository.save(horas);
    }

    public Map<String, Object> obtenerResumenHoras(Long empleadoId, LocalDate inicio, LocalDate fin) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new RuntimeException("Empleado no encontrado");
        }

        List<HorasTrabajadas> horas = horasRepository.findByEmpleadoEmpleadoIdAndFechaBetween(empleadoId, inicio, fin);

        double totalHoras = horas.stream()
                .mapToDouble(h -> h.getTotalHoras() != null ? h.getTotalHoras() : 0)
                .sum();

        Map<String, Double> horasPorTarea = horas.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getTipoTarea().name(),
                        Collectors.summingDouble(h -> h.getTotalHoras() != null ? h.getTotalHoras() : 0)
                ));

        long horasVerificadas = horas.stream().filter(HorasTrabajadas::getVerificado).count();
        long horasNoVerificadas = horas.stream().filter(h -> !h.getVerificado()).count();

        return Map.of(
                "empleadoId", empleadoId,
                "periodo", Map.of("inicio", inicio, "fin", fin),
                "totalHoras", totalHoras,
                "horasPorTarea", horasPorTarea,
                "totalRegistros", horas.size(),
                "horasVerificadas", horasVerificadas,
                "horasNoVerificadas", horasNoVerificadas,
                "promedioDiario", horas.isEmpty() ? 0 : totalHoras / horas.stream().map(HorasTrabajadas::getFecha).distinct().count()
        );
    }

    public Map<String, Object> obtenerResumenGeneral(LocalDate inicio, LocalDate fin) {
        List<HorasTrabajadas> todasHoras = horasRepository.findByFechaBetween(inicio, fin);

        double totalHorasGeneral = todasHoras.stream()
                .mapToDouble(h -> h.getTotalHoras() != null ? h.getTotalHoras() : 0)
                .sum();

        Map<String, Double> horasPorTareaGeneral = todasHoras.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getTipoTarea().name(),
                        Collectors.summingDouble(h -> h.getTotalHoras() != null ? h.getTotalHoras() : 0)
                ));

        Map<Long, Double> horasPorEmpleado = todasHoras.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getEmpleado().getEmpleadoId(),
                        Collectors.summingDouble(h -> h.getTotalHoras() != null ? h.getTotalHoras() : 0)
                ));

        return Map.of(
                "periodo", Map.of("inicio", inicio, "fin", fin),
                "totalHorasGeneral", totalHorasGeneral,
                "totalEmpleados", horasPorEmpleado.size(),
                "horasPorTarea", horasPorTareaGeneral,
                "horasPorEmpleado", horasPorEmpleado,
                "totalRegistros", todasHoras.size()
        );
    }

    public List<HorasTrabajadas> obtenerHorasDeHoy() {
        return horasRepository.findHorasDeHoy();
    }

    public HorasTrabajadas actualizarHoras(Long idHora, HorasTrabajadas horasActualizadas) {
        HorasTrabajadas horasExistentes = horasRepository.findById(idHora)
                .orElseThrow(() -> new RuntimeException("Horas no encontradas"));

        // Actualizar campos
        horasExistentes.setFecha(horasActualizadas.getFecha());
        horasExistentes.setHoraInicio(horasActualizadas.getHoraInicio());
        horasExistentes.setHoraFin(horasActualizadas.getHoraFin());
        horasExistentes.setTipoTarea(horasActualizadas.getTipoTarea());
        horasExistentes.setDescripcion(horasActualizadas.getDescripcion());

        // Recalcular horas automáticamente
        horasExistentes.calcularHoras();

        return horasRepository.save(horasExistentes);
    }

    public void eliminarHoras(Long idHora) {
        if (!horasRepository.existsById(idHora)) {
            throw new RuntimeException("Horas no encontradas");
        }
        horasRepository.deleteById(idHora);
    }

    // Método para obtener los tipos de tarea disponibles
    public List<Map<String, String>> obtenerTiposTarea() {
        return Arrays.stream(TipoTarea.values())
                .map(tipo -> Map.of(
                        "codigo", tipo.name(),
                        "descripcion", tipo.getDescripcion()
                ))
                .collect(Collectors.toList());
    }
}
